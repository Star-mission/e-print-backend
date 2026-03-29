package com.eprint.controller;

import com.eprint.dto.BlacklakeProcessQueryDTO;
import com.eprint.service.BlacklakeProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/blacklake")
@RequiredArgsConstructor
public class BlacklakeProcessController {

    private final BlacklakeProcessService blacklakeProcessService;

    @PostMapping("/processes/query")
    public ResponseEntity<Map<?, ?>> queryProcessList(@RequestBody BlacklakeProcessQueryDTO query) {
        return ResponseEntity.ok(blacklakeProcessService.queryProcessList(query));
    }
}
