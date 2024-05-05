package org.javarush.service;

import jakarta.persistence.EntityNotFoundException;
import org.javarush.domain.Status;
import org.javarush.domain.Task;
import org.javarush.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    public TaskRepository taskRepository;

    public List<Task> getAll(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return taskRepository.findAll(pageRequest).get().collect(Collectors.toList());
    }

    public int getAllCount() {
        return (int) (taskRepository.count());
    }

    @Transactional
    public Task update(int id, String description, Status status) {
        Task task = null;
        try {
            task = taskRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("task was not found");
        }
        task.setDescription(description);
        task.setStatus(status);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    public Task create(String description, Status status) {
        Task task = new Task();
        task.setStatus(status);
        task.setDescription(description);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = null;
        try {
            task = taskRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("task was not found");
        }
        taskRepository.delete(task);
    }
}
