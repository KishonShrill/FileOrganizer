// Java for System Function Imports
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
// Java Imports
import java.util.Map;
import java.util.HashMap;

public class FileOrganizer {
    private static String directoryPath;

    public static void main(String[] args) {
        // Specify the directory path
        String fileOrganizerName = "FileOrganizer.jar";
        File file = searchFile(new File(System.getProperty("user.dir")), fileOrganizerName);
        String absolutePath = file.getAbsolutePath();

        // Error Message in the Terminal
        if (file != null) {

            System.out.println("Absolute Path: " + absolutePath);
        } else {
            System.out.println("File not found.");
        }

        File parentDirectory = file.getParentFile();
        directoryPath = String.format("%s\\organizeFolder", parentDirectory);

        directoryChecker(directoryPath);
    }

    public static void directoryChecker(String directoryPath) {
        // Create a File object representing the directory
        File directory = new File(directoryPath);

        // Check if the specified path points to a directory
        if (directory.isDirectory()) {
            // Retrieve the list of files in the directory
            File[] files = directory.listFiles();
            fileProcessor(files);

        } else {
            System.out.println("Invalid directory path: " + directoryPath);
        }
    }

    // System.out.prints each file in the directory
    // Kind of like a debugger to check the contents inside.
    public static void fileProcessor(File[] files) {
        // Process each file in the directory
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // Retrieve the file name's extension for sorting
                    String fileName = file.getName();
                    String fileExtension = getFileExtension(fileName);

                    System.gc(); //DELETE THIS!!!
                    extensionSorter(fileExtension, file);
                }
            }
        }
    }

    public static void extensionSorter(String fileExtension, File file) {
        Map<String, String> fileExtensions = new HashMap<>();
        fileExtensions.put("pdf",   "PDFs");
        fileExtensions.put("png",   "Images");
        fileExtensions.put("jpg",   "Images");
        fileExtensions.put("jpeg",  "Images");
        fileExtensions.put("gif",   "Images");
        fileExtensions.put("doc",   "Documents");
        fileExtensions.put("docx",  "Documents");
        fileExtensions.put("pptx",   "Documents");
        fileExtensions.put("txt",   "Documents");
        fileExtensions.put("csv",   "Data");
        fileExtensions.put("xlsx",  "Data");
        fileExtensions.put("zip",   "Archives");
        fileExtensions.put("rar",   "Archives");
        fileExtensions.put("exe",   "Executable");
        fileExtensions.put("mp3",   "Music");
        fileExtensions.put("wav",   "Music");
        fileExtensions.put("mp4",   "Videos");
        fileExtensions.put("avi",   "Videos");
        fileExtensions.put("flv",   "Videos");
        fileExtensions.put("wmv",   "Videos");

        if (fileExtensions.containsKey(fileExtension)) {
            String category = fileExtensions.get(fileExtension);
            folderChecker(category, file);

        }
    }

    public static void folderChecker(String category, File file) {
        // What folder does it go?
        String folderPath = String.format("%s\\%s", directoryPath, category);
        // Get the value of the key to check for validity of folder
        File folder = new File(folderPath);

        // Check if the folder exists
        if (!folder.exists() || !folder.isDirectory()) {
            // Folder doesn't exist, create it
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("Folder created: " + folderPath);
            } else {
                System.out.println("Failed to create folder: " + folderPath);
            }
        } else {
            System.out.printf("\nFolder is already created: %s", category);
        }

        fileMover(file, category);
    }

    public static void fileMover(File file, String targetSubfolder) {
        if (file.isFile()) {
            File parentDirectory = file.getParentFile();
            File targetFolder = new File(parentDirectory, targetSubfolder);
            Path sourcePath = file.toPath();
            Path targetPath = targetFolder.toPath().resolve(file.getName());

            try {
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("\nFile moved successfully: " + file.getName());
            } catch (Exception e) {
                System.out.println("\nFailed to move file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public static String getFileExtension(String fileName) {
        // Find the last index of the dot character in the file name
        int dotIndex = fileName.lastIndexOf('.');
        // Check if a valid file extension exists
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // Extract the substring after the dot and convert it to lowercase
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        // Return an empty string if no file extension is found
        return "";
    }

    public static File searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File result = searchFile(file, fileName);
                    if (result != null) {
                        return result;
                    }
                } else if (file.isFile() && file.getName().equals(fileName)) {
                    return file;
                }
            }
        }
        return null;
    }
}