#version 330

const int MAX_DIRECTIONAL_LIGHTS = 4;
const int MAX_POINT_LIGHTS = 8;
const int MAX_SPOT_LIGHTS = 8;

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;
in vec3 vertexColor0;

uniform mat4 transformView;
uniform mat4 normalMatrix;

struct BaseLight
{
    vec3 color;
    float intensity;
};

struct DirectionalLight
{
    BaseLight base;
    vec3 direction;
};

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    BaseLight base;
    Attenuation atten;
    vec3 position;
    float range;
};

struct SpotLight
{
    PointLight pointLight;
    vec3 direction;
    float cutoff;
};

uniform vec3 BaseColor;
uniform vec3 ambientLight;
uniform sampler2D sampler;

uniform DirectionalLight directionalLights[MAX_DIRECTIONAL_LIGHTS];
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
    float diffuseFactor = dot(normal, -direction);
    
    vec4 diffuseColor = vec4(0,0,0,0);
    
    if(diffuseFactor > 0)
    {
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
    }
    
    return diffuseColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
    return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
    vec3 lightDirection = worldPos0 - pointLight.position;
    float distanceToPoint = length(lightDirection);
    
    if(distanceToPoint > pointLight.range)
        return vec4(0,0,0,0);
    
    lightDirection = normalize(lightDirection);
    
    vec4 color = calcLight(pointLight.base, lightDirection, normal);
    
    float attenuation = pointLight.atten.constant +
                         pointLight.atten.linear * distanceToPoint +
                         pointLight.atten.exponent * distanceToPoint * distanceToPoint +
                         0.0001;
                         
    return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal)
{
    vec3 lightDirection = normalize(worldPos0 - spotLight.pointLight.position);
    float spotFactor = dot(lightDirection, spotLight.direction);
    
    vec4 color = vec4(0,0,0,0);
    
    if(spotFactor > spotLight.cutoff)
    {
        color = calcPointLight(spotLight.pointLight, normal) *
                (1.0 - (1.0 - spotFactor)/(1.0 - spotLight.cutoff));
    }
    
    return color;
}

void main()
{ 
    vec4 totalLight = vec4(ambientLight,1);
    vec4 color = vec4(BaseColor, 1);
    vec4 textureColor = texture(sampler, texCoord0.xy);
    
    if(textureColor != vec4(0,0,0,0))
        color *= textureColor;
    
    vec3 normal = normalize(normal0);
    
	for(int i = 0; i < MAX_DIRECTIONAL_LIGHTS; i++)
        if(directionalLights[i].base.intensity > 0)
            totalLight += calcDirectionalLight(directionalLights[i], normal);
    
    for(int i = 0; i < MAX_POINT_LIGHTS; i++)
        if(pointLights[i].base.intensity > 0)
            totalLight += calcPointLight(pointLights[i], normal);
    
    for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
        if(spotLights[i].pointLight.base.intensity > 0)
            totalLight += calcSpotLight(spotLights[i], normal);
    
    gl_FragData[0] = color * totalLight * vec4(vertexColor0, 1.0);
	gl_FragData[1] = normalMatrix * vec4(normal0, 1);
	gl_FragData[2] = transformView * vec4(worldPos0,1);
}