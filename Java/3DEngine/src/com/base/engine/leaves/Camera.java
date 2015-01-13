package com.base.engine.leaves;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Vector3f;

public class Camera extends GameLeaf {
    protected Matrix4f projection, view;

    public Camera(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getViewProjection() {
        if(view == null) {
            Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
            Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
            Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos);

            return projection.mul(cameraRotation.mul(cameraTranslation));
        }

        Matrix4f v = view;
        System.out.println("asdasd");
        view = null;
        return projection.mul(v);
    }

    public Matrix4f getView() {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos);
        return cameraRotation.mul(cameraTranslation);
    }

    public void setView(Matrix4f view) { this.view = view; }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    @Override
    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addCamera(this);
    }
}
