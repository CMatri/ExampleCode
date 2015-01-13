#version 120
#include "lighting.glh"
#include "sampling.glh"
#include "lighting.fsh"

uniform DirectionalLight R_directionalLight;
varying vec3 normal0;

float calcShadowMapEffect(sampler2D shadowMap, vec4 initialShadowMapCoords) {
	vec3 shadowMapCoords = (initialShadowMapCoords.xyz / initialShadowMapCoords.w);

    if(inRange(shadowMapCoords.z) && inRange(shadowMapCoords.x) && inRange(shadowMapCoords.y)) {
	    return sampleVarianceShadowMap(shadowMap, shadowMapCoords.xy, shadowMapCoords.z, R_shadowVarianceMin, R_shadowLightBleedingReduction);
    } else {
        return 1.0f;
    }
}

void main() {
	vec3 directionToEye = normalize(C_eyePos - worldPos0);
    vec2 texCoords = calcTexCoordDisplacement(dispMap, tbnMatrix, directionToEye, texCoord0, dispScale, dispBias);
    vec3 normal = normalize(tbnMatrix * (255.0/128.0 * (texture2D(normalMap, texCoords.xy).xyz) - 1.));

    if(reflect == 1) {
        vec4 vClipReflection = mvpMatrix * vec4(objectPos0.xy, 0.0 , 1.0);
    	vec2 vDeviceReflection = vClipReflection.st / vClipReflection.q;
    	vec2 vTextureReflection = vec2(0.5, 0.5) + 0.5 * vDeviceReflection;

    	vec4 reflectionTextureColor = texture2D(R_reflectMap, vTextureReflection);

    	reflectionTextureColor.a = 1.0;

    	gl_FragColor = texture2D(R_reflectMap, texCoords.xy);//reflectionTextureColor;
    } else
        gl_FragColor = texture2D(diffuse, texCoords.xy) * calcDirectionalLight(R_directionalLight, normal, worldPos0) * calcShadowMapEffect(R_shadowMap, shadowMapCoords0);
}