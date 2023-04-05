package com.example.runwayproject.Controller;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
//testing
public class FileChooser {

    public static void main(String[] args) {



        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose a directory to save your file: ");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isDirectory()) {
                System.out.println(jfc.getSelectedFile());
            }
        }

    }

}



