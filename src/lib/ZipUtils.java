/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author princ
 */
public class ZipUtils {

        private final List<File> fileList;

        private List<String> paths;

        public ZipUtils() {
            fileList = new ArrayList<>();
            paths = new ArrayList<>(25);
        }

        public void zipIt(File sourceFile, File zipFile) throws IOException {
            if (sourceFile.isDirectory()) {
                byte[] buffer = new byte[1024];
                FileOutputStream fos = null;
                ZipOutputStream zos = null;

                try {

                    // This ensures that the zipped files are placed
                    // into a folder, within the zip file
                    // which is the same as the one been zipped
                    String sourcePath = sourceFile.getParentFile().getPath();
                    generateFileList(sourceFile);

                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(fos);

                    System.out.println("Output to Zip : " + zipFile);
                    Log.log(Log.NEW_LINE+"Output to Zip : " + zipFile+Log.NEW_LINE);
                    FileInputStream in = null;

                    for (File file : this.fileList) {
                        String path = file.getParent().trim();
                        path = path.substring(sourcePath.length());

                        if (path.startsWith(File.separator)) {
                            path = path.substring(1);
                        }

                        if (path.length() > 0) {
                            if (!paths.contains(path)) {
                                paths.add(path);
                                ZipEntry ze = new ZipEntry(path + "/");
                                zos.putNextEntry(ze);
                                zos.closeEntry();
                            }
                            path += "/";
                        }

                        String entryName = path + file.getName();
                        System.out.println("File Added : " + entryName);
                        Log.log("File Added : " + entryName+Log.NEW_LINE);
                        ZipEntry ze = new ZipEntry(entryName);

                        zos.putNextEntry(ze);
                        try {
                            in = new FileInputStream(file);
                            int len;
                            while ((len = in.read(buffer)) > 0) {
                                zos.write(buffer, 0, len);
                            }
                        } finally {
                            in.close();
                        }
                    }

                    zos.closeEntry();
                    System.out.println("Folder successfully compressed");
                    Log.log(Log.NEW_LINE+"Folder successfully compressed"+Log.NEW_LINE);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    Log.log(Log.NEW_LINE+"Error:"+ex.toString()+Log.NEW_LINE);
                } finally {
                    try {
                        zos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.log(Log.NEW_LINE+"Error:"+e.toString()+Log.NEW_LINE);
                    }
                }
            }
        }

        protected void generateFileList(File node) {

// add file only
            if (node.isFile()) {
                fileList.add(node);

            }

            if (node.isDirectory()) {
                File[] subNote = node.listFiles();
                for (File filename : subNote) {
                    generateFileList(filename);
                }
            }
        }
    }
