varying vec2 texCoord0;
varying mat3 tbnMatrix;
varying mat4 mvpMatrix;
varying vec4 shadowMapCoords0;
varying vec3 worldPos0;
varying vec3 objectPos0;

uniform sampler2D diffuse;
uniform sampler2D normalMap;
uniform sampler2D dispMap;

uniform float dispScale;
uniform float dispBias;
uniform float reflect;

uniform sampler2D R_reflectMap;
uniform sampler2D R_shadowMap;
uniform float R_shadowVarianceMin;
uniform float R_shadowLightBleedingReduction;
uniform float R_shadowBias;
uniform vec3 R_shadowTexelSize;