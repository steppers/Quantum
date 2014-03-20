#version 150 core

uniform sampler2D color_Tex;
uniform sampler2D normal_Tex;
uniform sampler2D position_Tex;
uniform sampler2D depth_Tex;
uniform sampler2D random_Tex;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

float zFar = 1000;
float zNear = 0.01;

float SSAOIntensity = 1;
float SSAORadius = 5.5;
float SSAOScale = 4;
float SSAOBias = 0.4;

float getDepth(vec2 uv){
	float tex = texture(depth_Tex, uv);
	float a = zFar / (zFar - zNear);
	float b = (zFar * zNear)/(zNear - zFar);
	return b/(tex - a);
}

vec3 getPosition(vec2 uv){
	return texture(position_Tex, pass_TextureCoord).xyz;
}

vec2 getRandom(vec2 uv){
	return normalize(texture(random_Tex, vec2(900,600) * uv / 64).xy * 2 - 1);
}

vec3 normalFromDepth(float depth, vec2 uv){
	vec2 offset1 = vec2(0, 0.001);
	vec2 offset2 = vec2(0.001, 0);
	
	float depth1 = getDepth(uv + offset1);
	float depth2 = getDepth(uv + offset2);
	
	vec3 p1 = vec3(offset1, depth1 - depth);
	vec3 p2 = vec3(offset2, depth2 - depth);
	
	vec3 normal = cross(p1, p2);
	normal.z *= -1;
	
	return normalize(normal);
}

float OcclusionFunction(float tcoord, vec2 uv, vec3 p, vec3 cnorm){
	vec3 diff = getPosition(tcoord + uv) - p;	
	float d = length(diff) * SSAOScale;
	vec3 v = normalize(diff);
	return max(0, dot(cnorm, v) - SSAOBias) * (1 / (1+d)) * SSAOIntensity;
}

float PostProcessSSAO(){
	vec2 vec[4] = vec2[4](vec2(1,0), vec2(-1,0), vec2(0,1), vec2(0,-1));
	
	float depth = getDepth(pass_TextureCoord);
	vec3 p = getPosition(pass_TextureCoord);
	vec3 n = normalFromDepth(depth, pass_TextureCoord);
	vec2 rand = getRandom(pass_TextureCoord);
	
	float ao = 0;
	float rad = SSAORadius/depth;
	
	int iterations = 4;
	for(int i = 0; i < iterations; i++){
		vec2 coord1 = reflect(vec[i], rand) * rad;
		vec2 coord2 = vec2(coord1.x*0.707 - coord1.y*0.707,
							coord1.x*0.707 + coord1.y*0.707);
							
		ao += OcclusionFunction(pass_TextureCoord, coord1*0.25, p, n);
		ao += OcclusionFunction(pass_TextureCoord, coord2*0.5, p, n);
		ao += OcclusionFunction(pass_TextureCoord, coord1*0.75, p, n);
		ao += OcclusionFunction(pass_TextureCoord, coord2, p, n);
	}
	
	ao /= (iterations*4);
	return ao;
}

void main(void) {
	float ssao = PostProcessSSAO();
	out_Color = texture(color_Tex, pass_TextureCoord) * (1 - ssao);
}