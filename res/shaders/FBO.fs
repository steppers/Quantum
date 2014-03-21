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

float SSAOIntensity = 3;
float SSAORadius = 7;
float SSAOScale = 20;
float SSAOBias = 0.2;

float getDepth(vec2 uv){
	return texture(depth_Tex, uv);
}

vec3 getPosition(vec2 uv){
	return texture(position_Tex, uv).xyz;
}

vec2 getRandom(vec2 uv){
	return normalize(texture(random_Tex, vec2(900,600) * uv / 64).xy * 2 - 1);
}

vec3 getNormal(vec2 uv){
    return normalize(texture(normal_Tex, uv).xyz * 2.0 - 1.0);
}

float OcclusionFunction(vec2 tcoord, vec2 uv, vec3 p, vec3 cnorm){
	vec3 diff = getPosition(tcoord + uv) - p;
	float d = length(diff) * SSAOScale;
	vec3 v = normalize(diff);
	return max(0, dot(cnorm, v) - SSAOBias) * (1 / (1+d)) * SSAOIntensity;
}

float PostProcessSSAO(){
	vec2 vec[4] = vec2[4](vec2(1,0), vec2(-1,0), vec2(0,1), vec2(0,-1));

	vec3 p = getPosition(pass_TextureCoord);
	vec3 n = getNormal(pass_TextureCoord);
	vec2 rand = getRandom(pass_TextureCoord);

	float ao = 0;
	float rad = SSAORadius/p.z;

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
    float SSAO = PostProcessSSAO();
	//out_Color = vec4(normalFromDepth(getDepth(pass_TextureCoord), pass_TextureCoord), 1.0);
	//out_Color = vec4(getPosition(pass_TextureCoord), 1.0);
	out_Color = texture(color_Tex, pass_TextureCoord);
}