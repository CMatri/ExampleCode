package com.base.engine.leaves;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class SpotLight extends PointLight {
    private float cutoff;

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation, float viewAngle, int shadowMapSizeAsPowerOf2, float shadowSoftness, float lightBleedReductionAmount, float minVariance) {
        super(color, intesity, attenuation);
        this.cutoff = (float) Math.cos(viewAngle / 2);

        setShader(new Shader("forward-spot"));
        if (shadowMapSizeAsPowerOf2 != 0)
            setShadowInfo(new ShadowInfo(new Matrix4f().initPerspective((float) Math.acos(cutoff) * 2, 1.0f, 0.1f, getRange()), true, shadowMapSizeAsPowerOf2, shadowSoftness,lightBleedReductionAmount, minVariance));
    }

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation, float viewAngle, int shadowMapSizeAsPowerOf2, float shadowSoftness, float lightBleedReductionAmount) {
        this(color, intesity, attenuation, viewAngle, shadowMapSizeAsPowerOf2, shadowSoftness, lightBleedReductionAmount, 0.00002f);
    }

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation, float viewAngle, int shadowMapSizeAsPowerOf2, float shadowSoftness) {
        this(color, intesity, attenuation, viewAngle, shadowMapSizeAsPowerOf2, shadowSoftness, 0.2f, 0.00002f);
    }

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation, float viewAngle, int shadowMapSizeAsPowerOf2) {
        this(color, intesity, attenuation, viewAngle, shadowMapSizeAsPowerOf2, 1.0f, 0.2f, 0.00002f);
    }

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation, float viewAngle) {
        this(color, intesity, attenuation, viewAngle, 0, 1.0f, 0.2f, 0.00002f);
    }

    public SpotLight(Vector3f color, float intesity, Attenuation attenuation) {
        this(color, intesity, attenuation, (float) Math.toRadians(170.0f), 0, 1.0f, 0.2f, 0.00002f);
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
