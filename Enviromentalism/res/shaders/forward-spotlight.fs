#version 330

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

//out vec4 fragColor;

struct BaseLight
{
	vec3 color;
	float intensity;
};

struct Attenuation //how quickly does light fade out as you increase distance
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
	float cutOff;
};

uniform sampler2D diffuse;
uniform vec3 eyePos;

uniform float specularIntensity;
uniform float specularExponent;

uniform SpotLight spotLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
	float diffuseFactor = dot(normal, -direction);
	
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);
	
	if(diffuseFactor > 0)
	{
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
		
		vec3 directionToEye = normalize(eyePos - worldPos0);
		vec3 reflectDirection = normalize(reflect(direction, normal));
		
		float specularFactor = dot(directionToEye, reflectDirection);
		specularFactor = pow(specularFactor, specularExponent);
		
		if(specularFactor > 0)
		{
			specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
		}
	}
	
	return diffuseColor + specularColor;
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDirection = worldPos0 - pointLight.position;
	float distanceToPoint = length(lightDirection);
	
	if(distanceToPoint > pointLight.range)
		return vec4(0, 0, 0, 0);
	
	lightDirection = normalize(lightDirection);
	
	vec4 color = calcLight(pointLight.base, lightDirection, normal);
	
	float attenuation = pointLight.atten.constant + 
						pointLight.atten.linear * distanceToPoint +
						pointLight.atten.exponent * distanceToPoint * distanceToPoint + 
						0.0001; 
						//add 0.0001 to avoid division by zero (in GLSL, if statements can run both choices anyway)
						
	return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal)
{
	vec3 lightDirection = normalize(worldPos0 - spotLight.pointLight.position);
	float spotFactor = dot(lightDirection, spotLight.direction);
	
	vec4 color = vec4(0, 0, 0, 0);
	
	if(spotFactor > spotLight.cutOff) //dot product gets smaller as two vectors diverge; cosine
	{
		color = calcPointLight(spotLight.pointLight, normal) * 
			(1.0 - (1.0 - spotFactor) / (1.0 - spotLight.cutOff));
	}
		
	return color;
}

void main()
{	
	gl_FragColor = texture2D(diffuse, texCoord0.xy) * calcSpotLight(spotLight, normalize(normal0));
}
