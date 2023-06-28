// Java for System Function Imports
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
// Java Imports
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * The FileOrganizer class is responsible for organizing files into specific folders based on their file extensions.
 * It searches for files in a specified directory and moves them to categorized folders.
 *
 * @author Chriscent Pingol
 * @version v.1.0
 * p.s - I put little comments ["//"] all over the code because I tend to forget how it all works again...
 * p.p.s - It's a memory thing...
 */

public class FileOrganizer {
    private static String directoryPath;

    public static void main(String[] args) {
        // Specify the directory path
        String path = FileOrganizer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        String jarDir = file.getParentFile().getAbsolutePath();
        directoryPath = String.format("%s\\organizerFolder", jarDir);

        // Error Message as Popup
        if (file != null) {
            showPopup("Absolute Path: " + directoryPath);
        } else {
            showErrorPopup("File not found.");
        }

        directoryChecker(directoryPath);
        showPopup("File Organizer Complete!\nCheck the folder to see your organized files!");
    }

    /**
     * Checks the specified directory path and organizes the files within the directory.
     * If the specified path points to a valid directory, it retrieves the list of files and processes them.
     * Each file is then passed to the extension sorter for categorization and subsequent moving.
     *
     * @param directoryPath the path of the directory to be checked and organized
     */

    public static void directoryChecker(String directoryPath) {
        // Create a File object representing the directory
        File directory = new File(directoryPath);

        // Check if the specified path points to a directory
        if (directory.isDirectory()) {
            // Retrieve the list of files in the directory
            File[] files = directory.listFiles();
            fileProcessor(files);
        } else {
            showErrorPopup("Invalid directory path: " + directoryPath);
        }
    }

    public static void fileProcessor(File[] files) {
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String fileExtension = getFileExtension(fileName);
                    System.gc(); // DELETE THIS!!!
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
        fileExtensions.put("jfif",  "Images");
        fileExtensions.put("aae",   "Images");
        fileExtensions.put("ico",   "Images");
        fileExtensions.put("webp",   "Images");
        fileExtensions.put("doc",   "Documents");
        fileExtensions.put("docx",  "Documents");
        fileExtensions.put("pptx",  "Documents");
        fileExtensions.put("psd",   "Documents");
        fileExtensions.put("txt",   "Documents");
        fileExtensions.put("log",   "Documents");
        fileExtensions.put("vtt",   "Documents");
        fileExtensions.put("torrent","Documents");
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
        fileExtensions.put("mov",   "Videos");

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
            // If Folder doesn't exist, create it
            boolean created = folder.mkdir();
            if (created) {
                showPopup("Folder created:\t" + category);
            } else {
                showErrorPopup("Failed to create folder: " + folderPath);
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
                showErrorPopup("Failed to move file: " + file.getName());
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

    public static void showPopup(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void showErrorPopup(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}