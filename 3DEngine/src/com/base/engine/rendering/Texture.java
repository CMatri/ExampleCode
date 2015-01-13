package com.base.engine.rendering;

import com.base.engine.core.Util;
import com.base.engine.rendering.resourceManagement.TextureResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class Texture {
    private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
    private String fileName;
    private TextureResource resource;

    public Texture(String fileName) {
        this(fileName, GL_TEXTURE_2D, new int[]{GL_LINEAR_MIPMAP_LINEAR}, new int[]{GL_RGBA8}, new int[]{GL_RGBA}, false, new int[]{GL_COLOR_ATTACHMENT0});
    }

    public Texture(String fileName, int textureTarget, int[] filter, int[] internalFormat, int[] format, boolean clamp, int[] attachment) {
        this.fileName = fileName;
        TextureResource oldResource = loadedTextures.get(fileName);

        if (oldResource != null) {
            resource = oldResource;
            resource.addReference();
        } else {
            resource = loadTexture(fileName, textureTarget, filter, internalFormat, format, clamp, attachment[0] == 0 ? attachment : null);
            loadedTextures.put(fileName, resource);
        }
    }

    public Texture(BufferedImage image, int textureTarget, int[] filter, int[] internalFormat, int[] format, boolean clamp, int[] attachment) {
        resource = createTexture(image, textureTarget, filter, internalFormat, format, clamp, attachment);
    }

    public Texture(String fileName, int textureTarget, int filter, int internalFormat, int format, boolean clamp, int attachment) {
        this(fileName, textureTarget, new int[]{filter}, new int[]{internalFormat}, new int[]{format}, clamp, new int[]{attachment});
    }

    public Texture(int width, int height, ByteBuffer data, int textureTarget, int[] filter, int[] internalFormat, int[] format, boolean clamp, int[] attachment) {
        resource = new TextureResource(textureTarget, width, height, attachment.length, data, filter, internalFormat, format, clamp, attachment);
    }

    public Texture(int width, int height, ByteBuffer data, int textureTarget, int filter, int internalFormat, int format, boolean clamp, int attachment) {
        resource = new TextureResource(textureTarget, width, height, 1, data, new int[]{filter}, new int[]{internalFormat}, new int[]{format}, clamp, new int[]{attachment});
    }

    @Override
    protected void finalize() {
        if (resource.removeReference() && !fileName.isEmpty()) {
            loadedTextures.remove(fileName);
        }
    }

    public void bind() {
        bind(0);
    }

    public void bind(int samplerSlot) {
        assert (samplerSlot >= 0 && samplerSlot <= 31);
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        resource.bind(0);
    }

    public int getID(int textureNum) {
        return resource.getId(textureNum);
    }

    private TextureResource loadTexture(String fileName, int textureTarget, int[] filter, int[] internalFormat, int[] format, boolean clamp, int[] attachment) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getHeight());

            ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) ((pixel) & 0xFF));
                    if (hasAlpha)
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte) (0xFF));
                }
            }

            buffer.flip();

            TextureResource resource = new TextureResource(textureTarget, image.getWidth(), image.getHeight(), 1, buffer, filter, internalFormat, format, clamp, attachment);

            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public TextureResource createTexture(BufferedImage image, int textureTarget, int[] filter, int[] internalFormat, int[] format, boolean clamp, int[] attachment) {
        try {
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getHeight());

            ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) ((pixel) & 0xFF));
                    if (hasAlpha)
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte) (0xFF));
                }
            }

            buffer.flip();

            TextureResource resource = new TextureResource(textureTarget, image.getWidth(), image.getHeight(), 1, buffer, filter, internalFormat, format, clamp, attachment);

            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public void bindAsRenderTarget() {
        resource.bindAsRenderTarget();
    }

    public int getWidth() {
        return resource.getWidth();
    }

    public int getHeight() {
        return resource.getHeight();
    }
}
