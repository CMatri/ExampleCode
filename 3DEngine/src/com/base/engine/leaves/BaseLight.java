package com.base.engine.leaves;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Shader;

public class BaseLight extends GameLeaf {
    private Vector3f color;
    private float intensity;
    private Shader shader;
    private ShadowInfo shadowInfo;

    public BaseLight(Vector3f color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addLight(this);
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public ShadowInfo getShadowInfo() {
        return shadowInfo;
    }

    protected void setShadowInfo(ShadowInfo shadowInfo) {
        this.shadowInfo = shadowInfo;
    }

    public ShadowCameraTransform calcShadowCameraTransform(Vector3f mainCameraPos, Quaternion mainCameraRot) {
        ShadowCameraTransform result = new ShadowCameraTransform();
        result.pos = getTransform().getTransformedPos();
        result.rot = getTransform().getTransformedRot();

        return result;
    }

    public class ShadowInfo {
        private Matrix4f projection;
        private boolean flipFaces;
        private float varianceMin;
        private float shadowSoftness;
        private float lightBleedReductionAmount;
        private int shadowMapSizeAsPowerOf2;

        public ShadowInfo(Matrix4f projection, boolean flipFaces, int shadowMapSizeAsPowerOf2, float shadowSoftness, float lightBleedReductionAmount, float varianceMin) {
            this.projection = projection;
            this.flipFaces = flipFaces;
            this.shadowMapSizeAsPowerOf2 = shadowMapSizeAsPowerOf2;
            this.shadowSoftness = shadowSoftness;
            this.lightBleedReductionAmount = lightBleedReductionAmount;
            this.varianceMin = varianceMin;
        }

        public ShadowInfo(Matrix4f projection, boolean flipFaces, int shadowMapSizeAsPowerOf2, float shadowSoftness, float lightBleedReductionAmount) {
            this(projection, flipFaces, shadowMapSizeAsPowerOf2, shadowSoftness, lightBleedReductionAmount, 0.00002f);
        }

        public ShadowInfo(Matrix4f projection, boolean flipFaces, int shadowMapSizeAsPowerOf2, float shadowSoftness) {
            this(projection, flipFaces, shadowMapSizeAsPowerOf2, shadowSoftness, 0.2f);
        }

        public ShadowInfo(Matrix4f projection, boolean flipFaces, int shadowMapSizeAsPowerOf2) {
            this(projection, flipFaces, shadowMapSizeAsPowerOf2, 1.0f);
        }

        public boolean isFlipFaces() {
            return flipFaces;
        }

        public void setFlipFaces(boolean flipFaces) {
            this.flipFaces = flipFaces;
        }

        public Matrix4f getProjection() {
            return projection;
        }

        public float getShadowSoftness() {
            return shadowSoftness;
        }

        public float getVarianceMin() {
            return varianceMin;
        }

        public float getLightBleedReductionAmount() {
            return lightBleedReductionAmount;
        }

        public int getShadowMapSizeAsPowerOf2() {
            return shadowMapSizeAsPowerOf2;
        }
    }

    public class ShadowCameraTransform {
        public Vector3f pos;
        public Quaternion rot;
    }
}
