package com.grommash88.app.controller;

import com.grommash88.app.dto.TaskRequestDTO;
import com.grommash88.app.service.TaskManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.grommash88.app.model.Task;
import com.grommash88.app.service.ConverterDtoToModel;

@Controller
@RequestMapping(value = "/tasks")
@Api(value = "Task Controller", description = "Api для работы с тасками")
@RequiredArgsConstructor
public class TaskController {

  private final TaskManagerService taskManagerService;

  // Реализация метода Post, создает новый объект и добавляет его в хранилище.
  @PostMapping
  @ApiOperation(value = "Создание таска", response = Long.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Task успешно зодан и добавлен в базу данных.")})
  public ResponseEntity addTask(@ApiParam(
      value = "Обьект класса TaskRequestDTO полученный от пользователя в теле запроса в виде JSON файла.",
      required = true)
      @ModelAttribute("taskRequestDTO") TaskRequestDTO taskRequestDTO) {

    Task newSavedTask = ConverterDtoToModel.convertDtoToModel(taskRequestDTO);
    taskManagerService.addTask(newSavedTask);
    return ResponseEntity.status(HttpStatus.CREATED).body(newSavedTask.getId());
  }

  //Реализация метода Get, возвращает список уже объектов.
  @GetMapping
  @ApiOperation(value = "Получение списка тасков", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Список тасков успешно получен.")})
  public ResponseEntity getTasks() {
    return new ResponseEntity(taskManagerService.getTasks(), HttpStatus.OK);
  }

  //Реализация метода Get, возвращает определенный обект соответствующий ключу(id),
  // или 404 ошибку(если обьекта с указанным id не существует)
  @GetMapping(value = "/{id}")
  @ApiOperation(value = "Получение таска соответствующего полученному id.", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Таск успешно получен."),
      @ApiResponse(code = 404, message = "Таска с таким id не существует!")
  })
  public ResponseEntity get(
      @ApiParam(value = "id запрашиваемого таска", required = true, example = "41")
      @PathVariable Long id) {

    Optional<Task> optionalTask = taskManagerService.getTask(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(optionalTask.get());

    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

  }

  //Реализация метода Delete, удаляет объект с указанным id из хранилища,
  //или 404 ошибку(если объекта с указанным id не существует)
  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Удаление таска соответствующего полученному id.", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Таск успешно удален."),
      @ApiResponse(code = 404, message = "Таска с таким id не существует!")
  })
  public ResponseEntity remove(
      @ApiParam(value = "id удаляемого таска", required = true, example = "41")
      @PathVariable Long id) {

    if (!taskManagerService.removeTask(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return new ResponseEntity(taskManagerService.getTasks(), HttpStatus.OK);
  }

  //Реализация метода Put, обновляет поле Description у существующего объекта(по-указанному id),
  //или 404 ошибку(если объекта с указанным id не существует)
  @PutMapping(value = "/{id}")
  @ApiOperation(value = "Изменение поля description у таска соответствующего полученному id",
      response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Таск успешно изменен."),
      @ApiResponse(code = 404, message = "Таска с таким id не существует!")
  })
  public ResponseEntity updateTask(
      @ApiParam(value = "id изменяемого таска", required = true, example = "41")
      @PathVariable Long id,
      @ApiParam(value = "новое описание изменяемого таска", required = true, example = "пойти поплавать")
      @RequestParam String newDescription) {

    if (!taskManagerService.updateTask(id, newDescription)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {

      return new ResponseEntity(taskManagerService.getTask(id), HttpStatus.OK);
    }
  }
}
