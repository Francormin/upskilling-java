package services;

import entities.Task;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests unitarios para probar los métodos de la clase TaskManager")
public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        this.taskManager = new TaskManager();
    }

    @Test
    @DisplayName("Comprobar que el método addTask agregue correctamente una Tarea")
    void testAddTask() {
        // WHEN
        Task taskMock = taskManager.addTask("Comprar leche");

        // THEN
        assertNotNull(taskMock.getId());
        assertEquals("Comprar leche", taskMock.getTitle());
        assertFalse(taskMock.isCompleted());
        assertNotNull(taskManager.getTaskById(taskMock.getId()));
        assertTrue(taskManager.getAllTasks().contains(taskMock));
    }

    @Test
    @DisplayName("Comprobar que el método deleteTask elimine correctamente una Tarea")
    void testDeleteTask() {
        // GIVEN
        Task taskMock = taskManager.addTask("Comprar leche");

        // WHEN
        taskManager.deleteTask(taskMock);

        // THEN
        assertEquals(0, taskManager.getAllTasks().size());
        assertFalse(taskManager.getAllTasks().contains(taskMock));
        assertNull(taskManager.getTaskById(taskMock.getId()));
    }

    @Test
    @DisplayName("Comprobar que el método completeTask establezca el campo 'completed' de una Tarea en true")
    void testCompleteTask() {
        // GIVEN
        Task taskMock = taskManager.addTask("Comprar leche");

        // WHEN
        taskManager.completeTask(taskMock);

        // THEN
        assertTrue(taskMock.isCompleted());
        assertTrue(taskManager.getTaskById(taskMock.getId()).isCompleted());
    }

    @Test
    @DisplayName("Comprobar que el método getAllTasks devuelva correctamente una Lista de Tareas")
    void testGetAllTasks() {
        // GIVEN
        Task taskMock = taskManager.addTask("Comprar leche");
        Task taskMock2 = taskManager.addTask("Comprar pan");

        // WHEN
        List<Task> tasks = taskManager.getAllTasks();

        // THEN
        assertTrue(tasks.contains(taskMock));
        assertTrue(tasks.contains(taskMock2));
        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("Comprobar que el método getTaskById devuelva correctamente una Tarea por su ID")
    void testGetTaskById() {
        // GIVEN
        Task taskMock = taskManager.addTask("Comprar leche");
        UUID taskId = taskMock.getId();

        // WHEN
        Task expectedTask = taskManager.getTaskById(taskId);

        // THEN
        assertNotNull(expectedTask);
        assertEquals(expectedTask, taskMock);
        assertEquals(expectedTask.getId(), taskId);
    }

}
