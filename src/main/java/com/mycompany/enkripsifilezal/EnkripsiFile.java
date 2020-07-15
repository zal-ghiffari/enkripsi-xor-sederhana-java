/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.enkripsifilezal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author LENOVO
 */
public class EnkripsiFile {
    public static void enkripsikuy(File src, File kunci, File dest)
            throws IOException {

        if (src.isDirectory()) {

            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdirs();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                enkripsikuy(srcFile, kunci, destFile);
            }

        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            /*Cipher cipher = null;
            try {
            cipher.init(Cipher.ENCRYPT_MODE, kunci);
            } catch (InvalidKeyException ex) {
            Logger.getLogger(EnkripsiFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
            cipher = Cipher.getInstance("AES");
            } catch (NoSuchPaddingException ex) {
            Logger.getLogger(EnkripsiFile.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            InputStream in = new FileInputStream(src);
            InputStream inkey = new FileInputStream(kunci);
            OutputStream out = new FileOutputStream(dest);
            //MessageDigest md = MessageDigest.getInstance("SHA-256");
            //byte[] digest = md.digest();
            
            byte[] buffer = new byte[1024];
            //String eknrip = new String(buffer, "UTF-8");
            //md.update(eknrip.getBytes());
            //byte[] kunci = "poltekssnrplk".getBytes();
            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0) {
                //out.write(cipher.update(buffer, 0, length));
                //out.flush();
                buffer[length] = (byte) ((in.read(buffer)^inkey.read(buffer)));
                //buffer = md.digest();
                //String hasil = Base64.getEncoder().encodeToString(buffer);
                //out.write(hasil.getBytes());
                out.write(buffer, 0, length);
            }
            //out.write(cipher.doFinal());
            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
}
