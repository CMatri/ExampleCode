package com.base.engine.leaves;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Shader;

public class DirectionalLight extends BaseLight {

    private Vector3f origColor;
    private float halfShadowArea;

    public DirectionalLight(Vector3f color, float intensity, int shadowMapSizeAsPowerOf2, float shadowArea, float shadowSoftness, float lightBleedReductionAmount, float minVariance) {
        super(color, intensity);

        this.origColor = color;
        this.halfShadowArea = shadowArea / 2;

        setShader(new Shader("forward-directional"));
        if (shadowMapSizeAsPowerOf2 != 0)
            setShadowInfo(new ShadowInfo(new Matrix4f().initOrthographic(-halfShadowArea, halfShadowArea, -halfShadowArea, halfShadowArea, -halfShadowArea, halfShadowArea), true, shadowMapSizeAsPowerOf2, shadowSoftness, lightBleedReductionAmount, minVariance));
    }

    public DirectionalLight(Vector3f color, float intesity, int shadowMapSizeAsPowerOf2, float shadowArea, float shadowSoftness, float lightBleedReductionAmount) {
        this(color, intesity, shadowMapSizeAsPowerOf2, shadowArea, shadowSoftness, lightBleedReductionAmount, 0.00002f);
    }

    public DirectionalLight(Vector3f color, float intesity, int shadowMapSizeAsPowerOf2, float shadowArea, float shadowSoftness) {
        this(color, intesity, shadowMapSizeAsPowerOf2, shadowArea, shadowSoftness, 0.2f, 0.00002f);
    }

    public DirectionalLight(Vector3f color, float intesity, int shadowMapSizeAsPowerOf2, float shadowArea) {
        this(color, intesity, shadowMapSizeAsPowerOf2, shadowArea, 1.0f, 0.2f, 0.00002f);
    }

    public DirectionalLight(Vector3f color, float intesity, int shadowMapSizeAsPowerOf2) {
        this(color, intesity, shadowMapSizeAsPowerOf2, 80, 1.0f, 0.2f, 0.00002f);
    }

    public DirectionalLight(Vector3f color, float intesity) {
        this(color, intesity, 0, 80, 1.0f, 0.2f, 0.00002f);
    }

    public ShadowCameraTransform calcShadowCameraTransform(Vector3f mainCameraPos, Quaternion mainCameraRot) {
        ShadowCameraTransform result = new ShadowCameraTransform();
        result.pos = mainCameraPos.add(mainCameraRot.getForward().mul(halfShadowArea));
        result.rot = getTransform().getTransformedRot();

        float worldTexelSize = (halfShadowArea * 2) / ((float) (1 << getShadowInfo().getShadowMapSizeAsPowerOf2()));

        Vector3f lightSpaceCameraPos = result.pos.rotate(result.rot.conjugate());

        lightSpaceCameraPos.setX(worldTexelSize * (float) Math.floor(lightSpaceCameraPos.getX() / worldTexelSize));
        lightSpaceCameraPos.setY(worldTexelSize * (float) Math.floor(lightSpaceCameraPos.getY() / worldTexelSize));

        result.pos = lightSpaceCameraPos.rotate(result.rot);

        return result;
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }
}
