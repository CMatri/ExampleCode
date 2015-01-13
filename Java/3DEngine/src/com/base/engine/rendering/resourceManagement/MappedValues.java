package com.base.engine.rendering.resourceManagement;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Texture;

import java.util.HashMap;

public abstract class MappedValues {
    private HashMap<String, Texture> textureHashMap;

    private HashMap<String, Vector3f> vector3fHashMap;
    private HashMap<String, Float> floatHashMap;

    public MappedValues() {
        vector3fHashMap = new HashMap<String, Vector3f>();
        floatHashMap = new HashMap<String, Float>();
        textureHashMap = new HashMap<String, Texture>();
    }

    public void setVector3f(String name, Vector3f vector3f) {
        vector3fHashMap.put(name, vector3f);
    }

    public void setFloat(String name, float f) {
        floatHashMap.put(name, f);
    }

    public void setTexture(String name, Texture texture) {
        textureHashMap.put(name, texture);
    }
    
    public Vector3f getVector3f(String name) {
        Vector3f result = vector3fHashMap.get(name);
        return (result != null ? result : new Vector3f(0, 0, 0));
    }

    public float getFloat(String name) {
        Float result = floatHashMap.get(name);
        return (result != null ? result : 0);
    }

    public Texture getTexture(String name) {
        Texture result = textureHashMap.get(name);
        return (result != null ? result : (getDefault(name)));
    }

    public Texture getDefault(String name) {
        Texture result;
        if(name.equals("diffuse"))
            result = new Texture("test.png");
        else if (name.equals("normalMap"))
            result = new Texture("default_normal.jpg");
        else if (name.equals("dispMap"))
            result = new Texture("default_disp.png");
        else
            result = new Texture("test.png");
        return result;
    }
}
