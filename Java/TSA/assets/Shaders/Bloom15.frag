#import "Common/ShaderLib/MultiSample.glsllib"

uniform COLORTEXTURE m_Texture;

uniform sampler2D BloomTex;

in vec2 texCoord;

void main(){
vec4 colorRes = getColor(m_Texture,texCoord);
vec4 bloom = texture2D(BloomTex, texCoord);
gl_FragColor = bloom * 0.8 + colorRes;
}