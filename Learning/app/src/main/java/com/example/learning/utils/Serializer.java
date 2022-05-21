package com.example.learning.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public abstract class Serializer {
    public static void serialize(String filename, Object object, Context context){
        try{
            FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream cos;
            try{
                cos = new ObjectOutputStream(file);
                cos.writeObject(object);
                cos.flush();
                cos.close();
            }catch (IOException iex){
                iex.printStackTrace();
            }
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static Object deSerialize(String filename, Context context){
        try{
            FileInputStream file = context.openFileInput(filename);
            ObjectInputStream cis;
            try{
                cis = new ObjectInputStream(file);
                try{
                    Object object = cis.readObject();
                    cis.close();
                    return object;
                }catch(ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }
            }catch(StreamCorruptedException sce){
                sce.printStackTrace();
            }catch(IOException iex){
                iex.printStackTrace();
            }
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void logout(String filename, Context context){
        try{
            FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream cos;
            try{
                cos = new ObjectOutputStream(file);
                cos.write(new String("").getBytes());
                cos.flush();
                cos.close();
            }catch (IOException iex){
                iex.printStackTrace();
            }
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
