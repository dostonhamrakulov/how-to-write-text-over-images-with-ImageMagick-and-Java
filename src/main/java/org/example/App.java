package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        String ORIGINAL_IMAGES_DIRECTORY = "../Projects/shaxsiy/image_printer/images";
        String OUTPUT_IMAGES_DIRECTORY = "../Projects/shaxsiy/image_printer/images/transformed";

        File imageDirectory = new File(ORIGINAL_IMAGES_DIRECTORY);

        if (!imageDirectory.isDirectory() || imageDirectory.listFiles() == null) {
            return;
        }

        File newImageDirectory = new File(OUTPUT_IMAGES_DIRECTORY);

        for (File imageFile : imageDirectory.listFiles()) {
            String name = FilenameUtils.getExtension(imageFile.getName());

            if (!"jpg".equalsIgnoreCase(name) && !"png".equalsIgnoreCase(name)) {
                continue;
            }

            Path temporaryFile = Files.createTempFile("temp", ".jpg");

            try {
                final IMOperation op = new IMOperation();
                op.addImage(imageFile.getAbsolutePath());
                op.font("Helvetica");
                op.gravity("South");
                op.pointsize(100);
                op.fill("#c10000");
                op.annotate(0, 0, 0, 100, "barso.uz");
                op.addImage(temporaryFile.toFile().getAbsolutePath());

                final ConvertCmd con = new ConvertCmd();
                con.run(op);
            } catch (IM4JavaException e) {
                e.printStackTrace();
            }

            FileUtils.moveToDirectory(temporaryFile.toFile(), newImageDirectory, true);
        }
    }
}
