uniform sampler2D m_Texture;
varying vec2 texCoord;
uniform float m_blur;

void main() {
    vec4 color = texture2D(m_Texture, texCoord);

    float blurSize = m_blur;

    vec4 sum = vec4(0.0);

    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y - 4.0*blurSize)) * 0.05;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y - 3.0*blurSize)) * 0.09;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y - 2.0*blurSize)) * 0.12;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y - blurSize)) * 0.15;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y)) * 0.16;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y + blurSize)) * 0.15;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y + 2.0*blurSize)) * 0.12;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y + 3.0*blurSize)) * 0.09;
    sum += texture2D(m_Texture, vec2(texCoord.x, texCoord.y + 4.0*blurSize)) * 0.05;
    
    gl_FragColor = sum;
}

