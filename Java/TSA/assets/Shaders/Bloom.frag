uniform sampler2D m_Texture;
uniform sampler2D BloomTex;
varying vec2 texCoord;

void main() {
    vec4 color = texture2D(m_Texture, texCoord);
    vec4 bloomColor = texture2D(BloomTex, texCoord);

    gl_FragColor = color + bloomColor;
}