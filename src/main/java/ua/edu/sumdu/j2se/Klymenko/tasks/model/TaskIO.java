package ua.edu.sumdu.j2se.Klymenko.tasks.model;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Class for working with tasks (tasks list) serialization
 */
public class TaskIO implements Serializable{
    private static final Logger logger = Logger.getLogger(TaskIO.class);

    /**
     * Цrites tasks from the list to the stream in binary format
     *
     * @param tasks list of tasks
     * @param out output task stream
     * @exception IOException
     */
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(out))) {
            stream.writeInt(tasks.size());
            for (Task task : tasks) {
                stream.writeInt(task.getTitle().length());
                stream.writeUTF(task.getTitle());

                if (task.isActive()) {
                    stream.writeInt(1);
                } else {
                    stream.writeInt(0);
                }

                int interval = task.getRepeatInterval();
                stream.writeInt(interval);

                long start = task.getStartTime().toEpochSecond(ZoneOffset.UTC);
                stream.writeLong(start);

                if (interval != 0) {
                    long end = task.getEndTime().toEpochSecond(ZoneOffset.UTC);
                    stream.writeLong(end);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from the stream to this task list
     *
     * @param tasks list of tasks
     * @param in input task stream
     * @throws IOException
     */
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        Task task;
        LocalDateTime start;
        LocalDateTime end;

        try (DataInputStream stream = new DataInputStream(new BufferedInputStream(in))) {
            int size = stream.readInt();
            for (int i = 0; i < size; i++) {
                int length = stream.readInt();
                String title = stream.readUTF();
                int isActive = stream.readInt();
                int interval = stream.readInt();

                boolean active = false;
                if (isActive == 1) active = true;

                start = LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC);
                task = new Task(title, start);

                if (interval != 0) {
                    end = LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC);
                    task = new Task(title, start, end, interval);
                }
                tasks.add(task);
                task.setActive(active);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks from the list to a file
     *
     * @param tasks list of tasks
     * @param file  target file
     * @throws FileNotFoundException
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws FileNotFoundException {
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads tasks from a file into a task list
     *
     * @param tasks list of tasks
     * @param file  source file
     */
    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks from a list to a stream in JSON format
     *
     * @param tasks list of tasks
     * @param out   output stream
     * @throws IOException
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(json);
            writer.flush();
        }
    }

    /**
     * Reads tasks from a stream to a list
     *
     * @param tasks list of tasks
     * @param in    input stream
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader reader = new BufferedReader(in)) {
            String text;
            while ((text = reader.readLine()) != null) {
                AbstractTaskList taskList = new Gson().fromJson(text, tasks.getClass());
                for (Task task : taskList) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks to a JSON file
     *
     * @param tasks list of tasks
     * @param file target file
     * @exception IOException
     */
    public static void writeText(AbstractTaskList tasks, File file) {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from a JSON file
     *
     * @param tasks list of tasks
     * @param file source JSON file
     * @exception IOException
     */
    public static void readText(AbstractTaskList tasks, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = reader.readLine()) != null) {
                AbstractTaskList taskList = new Gson().fromJson(text, tasks.getClass());
                for (Task task : taskList) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**
 * Save data (serialise task) to json file
 *
 * @param list - task list that must be saved
 * @param path file name (path to file)
 * @exception IOException
 * */
    public static void save2File(AbstractTaskList list, String path) {
        Path curPath = FileSystems.getDefault().getPath(path).toAbsolutePath();

        try {
            Files.deleteIfExists(curPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("File was not found");
        }
        File distFile = new File(String.valueOf(curPath.toFile()));

        TaskIO.writeText(list, distFile);
        logger.info("The task list has been saved to " + path);
    }

    /**
     * Read tasl list (deserialize) from json file
     *
     * @param list - task list that must be saved
     * @param path file name (path to file)
     */
    public static void readFromFile(AbstractTaskList list, String path) {
        Path curPath = FileSystems.getDefault().getPath(path).toAbsolutePath();

        if (curPath.toFile().exists()) {
            File destFile = new File(String.valueOf(curPath.toFile()));
            TaskIO.readText(list, destFile);
            logger.info("The task list has been read from " + path);
        } else {
            logger.error("File " + path + "was not found!");
        }
    }
    /**
     * Method to avoid problem with serealize
     * not used now
     */
    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }
}