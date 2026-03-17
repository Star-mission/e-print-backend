package com.eprint.service;

import com.eprint.dto.UserDTO;
import com.eprint.entity.User;
import com.eprint.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 创建用户
     */
    @Transactional
    public UserDTO createUser(UserDTO userDTO, String passwordHash) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }

        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordHash);
        user.setFullName(userDTO.getFullName());
        user.setIsActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : true);

        // 设置权限
        user.setOrderSubmit(userDTO.getOrder_submit() != null ? userDTO.getOrder_submit() : false);
        user.setOrderAudit(userDTO.getOrder_audit() != null ? userDTO.getOrder_audit() : false);
        user.setWorkSubmit(userDTO.getWork_submit() != null ? userDTO.getWork_submit() : false);
        user.setWorkAudit(userDTO.getWork_audit() != null ? userDTO.getWork_audit() : false);
        user.setOrderCheck(userDTO.getOrder_check() != null ? userDTO.getOrder_check() : false);
        user.setWorkCheck(userDTO.getWork_check() != null ? userDTO.getWork_check() : false);
        user.setPmcCheck(userDTO.getPmc_check() != null ? userDTO.getPmc_check() : false);
        user.setPmcEdit(userDTO.getPmc_edit() != null ? userDTO.getPmc_edit() : false);
        user.setDelieveCheck(userDTO.getDelieve_check() != null ? userDTO.getDelieve_check() : false);
        user.setDelieveEdit(userDTO.getDelieve_edit() != null ? userDTO.getDelieve_edit() : false);
        user.setIsSAL(userDTO.getIsSAL() != null ? userDTO.getIsSAL() : false);
        user.setIsPUR(userDTO.getIsPUR() != null ? userDTO.getIsPUR() : false);
        user.setIsOUT(userDTO.getIsOUT() != null ? userDTO.getIsOUT() : false);
        user.setIsMNF(userDTO.getIsMNF() != null ? userDTO.getIsMNF() : false);
        user.setIsADM(userDTO.getIsADM() != null ? userDTO.getIsADM() : false);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    /**
     * 根据用户名查询用户
     */
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToDTO(user);
    }

    /**
     * 根据用户ID查询用户
     */
    public UserDTO getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToDTO(user);
    }

    /**
     * 获取所有用户
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 更新用户信息
     */
    @Transactional
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 更新基础信息
        if (userDTO.getFullName() != null) user.setFullName(userDTO.getFullName());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getIsActive() != null) user.setIsActive(userDTO.getIsActive());

        // 更新权限
        if (userDTO.getOrder_submit() != null) user.setOrderSubmit(userDTO.getOrder_submit());
        if (userDTO.getOrder_audit() != null) user.setOrderAudit(userDTO.getOrder_audit());
        if (userDTO.getWork_submit() != null) user.setWorkSubmit(userDTO.getWork_submit());
        if (userDTO.getWork_audit() != null) user.setWorkAudit(userDTO.getWork_audit());
        if (userDTO.getOrder_check() != null) user.setOrderCheck(userDTO.getOrder_check());
        if (userDTO.getWork_check() != null) user.setWorkCheck(userDTO.getWork_check());
        if (userDTO.getPmc_check() != null) user.setPmcCheck(userDTO.getPmc_check());
        if (userDTO.getPmc_edit() != null) user.setPmcEdit(userDTO.getPmc_edit());
        if (userDTO.getDelieve_check() != null) user.setDelieveCheck(userDTO.getDelieve_check());
        if (userDTO.getDelieve_edit() != null) user.setDelieveEdit(userDTO.getDelieve_edit());
        if (userDTO.getIsSAL() != null) user.setIsSAL(userDTO.getIsSAL());
        if (userDTO.getIsPUR() != null) user.setIsPUR(userDTO.getIsPUR());
        if (userDTO.getIsOUT() != null) user.setIsOUT(userDTO.getIsOUT());
        if (userDTO.getIsMNF() != null) user.setIsMNF(userDTO.getIsMNF());
        if (userDTO.getIsADM() != null) user.setIsADM(userDTO.getIsADM());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    /**
     * 更新最后登录时间
     */
    @Transactional
    public void updateLastLogin(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 删除用户
     */
    @Transactional
    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        userRepository.delete(user);
    }

    /**
     * 转换为 DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setIsActive(user.getIsActive());
        dto.setOrder_submit(user.getOrderSubmit());
        dto.setOrder_audit(user.getOrderAudit());
        dto.setWork_submit(user.getWorkSubmit());
        dto.setWork_audit(user.getWorkAudit());
        dto.setOrder_check(user.getOrderCheck());
        dto.setWork_check(user.getWorkCheck());
        dto.setPmc_check(user.getPmcCheck());
        dto.setPmc_edit(user.getPmcEdit());
        dto.setDelieve_check(user.getDelieveCheck());
        dto.setDelieve_edit(user.getDelieveEdit());
        dto.setIsSAL(user.getIsSAL());
        dto.setIsPUR(user.getIsPUR());
        dto.setIsOUT(user.getIsOUT());
        dto.setIsMNF(user.getIsMNF());
        dto.setIsADM(user.getIsADM());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }
}
