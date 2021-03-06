#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 uv;
layout (location = 2) in vec3 normal;
layout (location = 3) in vec3 vertexColor;

out vec2 texCoord0;
out vec3 normal0;
out vec3 worldPos0;
out vec3 vertexColor0;

uniform mat4 transform;
uniform mat4 transformProjected;

void main(){
	gl_Position = transformProjected * vec4(position, 1.0);
	texCoord0 = uv;
	normal0 = (transform * vec4(normal, 0.0)).xyz;
	worldPos0 = (transform * vec4(position, 1.0)).xyz;
	vertexColor0 = vertexColor;
}