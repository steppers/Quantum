#version 330

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

uniform mat4 transformView;
uniform mat4 normalMatrix;
uniform vec3 ambientIntensity;

uniform sampler2D sampler;

void main()
{
    gl_FragData[0] = texture2D(sampler, texCoord0.xy) * vec4(ambientIntensity, 1);
	gl_FragData[1] = normalMatrix * vec4(normal0, 1);
	gl_FragData[2] = transformView * vec4(worldPos0,1);
}