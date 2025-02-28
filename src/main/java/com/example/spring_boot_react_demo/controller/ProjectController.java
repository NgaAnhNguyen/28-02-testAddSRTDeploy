package com.example.spring_boot_react_demo.controller;

import com.example.spring_boot_react_demo.model.dto.request.CreateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.request.UpdateProjectRequest;
import com.example.spring_boot_react_demo.model.dto.response.ApiResponse;
import com.example.spring_boot_react_demo.model.dto.response.CreateProjectResponse;
import com.example.spring_boot_react_demo.model.dto.response.ProjectResponse;
import com.example.spring_boot_react_demo.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/project")
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    @Autowired
    ProjectService projectService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/createProject")
    public ApiResponse<CreateProjectResponse> createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        return ApiResponse.<CreateProjectResponse>builder()
                .result(projectService.createProject(createProjectRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<ProjectResponse> getProjectById(@RequestParam Long id) {
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.getProject(id))
                .build();
    }

    @PostMapping("/updateProject")
    public ApiResponse<String> UpdateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {
        return ApiResponse.<String>builder()
                .result(projectService.updateProject(updateProjectRequest))
                .build();
    }

    @PostMapping("/getSrt")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "❌ Không nhận được file SRT!";
        }

        try {
            // Lưu file vào thư mục tạm thời
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File savedFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(savedFile);

            System.out.println("✅ Đã nhận được file: " + savedFile.getAbsolutePath());

            return "✅ Spring Boot đã nhận file: " + file.getOriginalFilename();
        } catch (IOException e) {
            return "❌ Lỗi khi lưu file: " + e.getMessage();
        }
    }
}
