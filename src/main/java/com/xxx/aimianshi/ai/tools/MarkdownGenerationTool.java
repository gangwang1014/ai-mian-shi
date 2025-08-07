package com.xxx.aimianshi.ai.tools;

import cn.hutool.core.io.FileUtil;
import com.xxx.aimianshi.ai.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * 生成 markdown 文档
 */
public class MarkdownGenerationTool {

    private static final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";
    private static final String FILE_EXT = ".md";
    @Tool(description = "Generate a markdown file with given content", returnDirect = false)
    public String generateMarkdown(
            @ToolParam(description = "Name of the file to save the generated markdown") String fileName,
            @ToolParam(description = "Content to be included in the markdown") String content) {
        // 创建文件保存目录
        File directory = new File(FILE_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return "Error: Could not create directory for saving files.";
            }
        }

        // 设置文件路径（根据传入的文件名）
        String filePath = FILE_DIR + "/" + fileName + FILE_EXT;
        File markdownFile = new File(filePath);

        try {
            FileUtil.writeString(content, markdownFile, "UTF-8");
        } catch (Exception e) {
            return "Error: Could not write to file. " + e.getMessage();
        }

        // 返回成功消息
        return "Markdown file generated successfully at: " + filePath;
    }
}
