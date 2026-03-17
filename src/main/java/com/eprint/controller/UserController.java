package com.eprint.controller;

import com.eprint.dto.UserDTO;
import com.eprint.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 创建用户
     * POST /api/users/create
     */
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody Map<String, Object> request) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId((String) request.get("userId"));
            userDTO.setUsername((String) request.get("username"));
            userDTO.setEmail((String) request.get("email"));
            userDTO.setFullName((String) request.get("fullName"));
            userDTO.setIsActive((Boolean) request.get("isActive"));

            // 设置权限
            userDTO.setOrder_submit((Boolean) request.get("order_submit"));
            userDTO.setOrder_audit((Boolean) request.get("order_audit"));
            userDTO.setWork_submit((Boolean) request.get("work_submit"));
            userDTO.setWork_audit((Boolean) request.get("work_audit"));
            userDTO.setOrder_check((Boolean) request.get("order_check"));
            userDTO.setWork_check((Boolean) request.get("work_check"));
            userDTO.setPmc_check((Boolean) request.get("pmc_check"));
            userDTO.setPmc_edit((Boolean) request.get("pmc_edit"));
            userDTO.setDelieve_check((Boolean) request.get("delieve_check"));
            userDTO.setDelieve_edit((Boolean) request.get("delieve_edit"));
            userDTO.setIsSAL((Boolean) request.get("isSAL"));
            userDTO.setIsPUR((Boolean) request.get("isPUR"));
            userDTO.setIsOUT((Boolean) request.get("isOUT"));
            userDTO.setIsMNF((Boolean) request.get("isMNF"));
            userDTO.setIsADM((Boolean) request.get("isADM"));

            String passwordHash = (String) request.get("passwordHash");
            UserDTO result = userService.createUser(userDTO, passwordHash);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error creating user", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 根据用户名查询用户
     * GET /api/users/findByUsername?username=admin
     */
    @GetMapping("/findByUsername")
    public ResponseEntity<UserDTO> findByUsername(@RequestParam("username") String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error finding user by username", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据用户ID查询用户
     * GET /api/users/findByUserId?userId=admin
     */
    @GetMapping("/findByUserId")
    public ResponseEntity<UserDTO> findByUserId(@RequestParam("userId") String userId) {
        try {
            UserDTO user = userService.getUserByUserId(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error finding user by userId", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取所有用户
     * GET /api/users/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 更新用户信息
     * POST /api/users/update
     */
    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody Map<String, Object> request) {
        try {
            String userId = (String) request.get("userId");

            UserDTO userDTO = new UserDTO();
            userDTO.setFullName((String) request.get("fullName"));
            userDTO.setEmail((String) request.get("email"));
            userDTO.setIsActive((Boolean) request.get("isActive"));

            // 设置权限
            userDTO.setOrder_submit((Boolean) request.get("order_submit"));
            userDTO.setOrder_audit((Boolean) request.get("order_audit"));
            userDTO.setWork_submit((Boolean) request.get("work_submit"));
            userDTO.setWork_audit((Boolean) request.get("work_audit"));
            userDTO.setOrder_check((Boolean) request.get("order_check"));
            userDTO.setWork_check((Boolean) request.get("work_check"));
            userDTO.setPmc_check((Boolean) request.get("pmc_check"));
            userDTO.setPmc_edit((Boolean) request.get("pmc_edit"));
            userDTO.setDelieve_check((Boolean) request.get("delieve_check"));
            userDTO.setDelieve_edit((Boolean) request.get("delieve_edit"));
            userDTO.setIsSAL((Boolean) request.get("isSAL"));
            userDTO.setIsPUR((Boolean) request.get("isPUR"));
            userDTO.setIsOUT((Boolean) request.get("isOUT"));
            userDTO.setIsMNF((Boolean) request.get("isMNF"));
            userDTO.setIsADM((Boolean) request.get("isADM"));

            UserDTO result = userService.updateUser(userId, userDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating user", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除用户
     * DELETE /api/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新最后登录时间
     * POST /api/users/updateLastLogin
     */
    @PostMapping("/updateLastLogin")
    public ResponseEntity<Void> updateLastLogin(@RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            userService.updateLastLogin(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating last login", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
