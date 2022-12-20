package com.bside.breadgood.system.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2022/12/20. create by IntelliJ IDEA.
 *
 * <p> 시스템 health check용 API 컨트롤러 </p>
 *
 * @author Yeonha Kim
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/system")
public class SystemController {

  @GetMapping("/healthy")
  public String checkHealthy() {
    return "system is running...";
  }

}
