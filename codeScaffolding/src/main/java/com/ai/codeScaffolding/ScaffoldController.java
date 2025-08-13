package com.ai.codeScaffolding;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/scaffold")
public class ScaffoldController {

    @PostMapping("/crud")
    public ResponseEntity<byte[]> generateCrud(@RequestBody EntityRequest request) throws Exception {
        Path tempDir = Files.createTempDirectory("scaffold");
        Path srcDir = tempDir.resolve("src/main/java/" + request.getPackageName().replace(".", "/"));
        Files.createDirectories(srcDir);

        // Generate files
        generateEntityFile(request, srcDir);
        generateRepositoryFile(request, srcDir);
        generateServiceFile(request, srcDir);
        generateControllerFile(request, srcDir);

        // Create zip
        File zipFile = new File(tempDir.toFile(), request.getEntityName() + "-crud.zip");
        ZipUtil.pack(tempDir.toFile(), zipFile);

        // Return zip as byte array
        byte[] zipBytes = Files.readAllBytes(zipFile.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(zipBytes);
    }

    private void generateEntityFile(EntityRequest request, Path srcDir) throws Exception {
        StringBuilder code = new StringBuilder();
        code.append("package ").append(request.getPackageName()).append(";\n\n");
        code.append("import jakarta.persistence.*;\n\n");
        code.append("@Entity\npublic class ").append(request.getEntityName()).append(" {\n");

        for (FieldRequest field : request.getFields()) {
            code.append("    private ").append(field.getType()).append(" ").append(field.getName()).append(";\n");
        }
        code.append("}\n");

        writeFile(srcDir.resolve(request.getEntityName() + ".java"), code.toString());
    }

    private void generateRepositoryFile(EntityRequest request, Path srcDir) throws Exception {
        String code = "package " + request.getPackageName() + ";\n\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n\n" +
                "public interface " + request.getEntityName() + "Repository extends JpaRepository<" +
                request.getEntityName() + ", Long> {}\n";
        writeFile(srcDir.resolve(request.getEntityName() + "Repository.java"), code);
    }

    private void generateServiceFile(EntityRequest request, Path srcDir) throws Exception {
        String code = "package " + request.getPackageName() + ";\n\n" +
                "import java.util.*;\n" +
                "import org.springframework.stereotype.Service;\n\n" +
                "@Service\n" +
                "public class " + request.getEntityName() + "Service {\n" +
                "    // Service logic here\n}\n";
        writeFile(srcDir.resolve(request.getEntityName() + "Service.java"), code);
    }

    private void generateControllerFile(EntityRequest request, Path srcDir) throws Exception {
        String code = "package " + request.getPackageName() + ";\n\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import java.util.*;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + request.getEntityName().toLowerCase() + "s\")\n" +
                "public class " + request.getEntityName() + "Controller {\n" +
                "    // CRUD endpoints here\n}\n";
        writeFile(srcDir.resolve(request.getEntityName() + "Controller.java"), code);
    }

    private void writeFile(Path path, String content) throws Exception {
        Files.createDirectories(path.getParent());
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(content.getBytes());
        }
    }
}

