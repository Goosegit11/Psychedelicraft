#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

in vec2 texCoord;

uniform float ticks;
uniform vec4 pulses;
uniform float colorSafeMode;
uniform vec4 worldColorization;

out vec4 fragColor;

void main() {
	fragColor = texture(DiffuseSampler, texCoord);
	float fogCoord = texture(DepthSampler, texCoord).r;

	if (pulses.a > 0.0) {
		float pulseA = (sin((fogCoord - ticks) / 5.0) - 0.4) * pulses.a;

		if (pulseA > 0.0) {
		  fragColor = mix(fragColor.rgb, (fragColor.rgb + 1.0) * pulses.rgb, pulseA);
		}
	}

	if (worldColorization.a > 0.0) {
		vec3 c1 = fragColor.rgb;
		vec3 c2 = worldColorization.rgb;

		float distR = sqrt((c1.r - c2.r) * (c1.r - c2.r));
		float distG = sqrt((c1.g - c2.g) * (c1.g - c2.g));
		float distB = sqrt((c1.b - c2.b) * (c1.b - c2.b));

		float dist = clamp((distR + distG + distB), 0.0, 1.0);
		for (int i = 0; i < 4; i++) {
			dist *= dist;
		}

		float harmonizeStrength = dist * 3.0;
		harmonizeStrength += (sin((fogCoord - ticks) * 0.1434234) - 0.4) * 0.8;
		harmonizeStrength += (sin((fogCoord - ticks) * -0.12313) - 0.2) * 0.8;
		harmonizeStrength += sin((fogCoord - ticks) * -0.051233) * 0.2 * sin(ticks * 0.1321334);
		harmonizeStrength = clamp(harmonizeStrength, 0.0, 3.0);

		vec3 harmonizedColor;

		if (harmonizeStrength < 1.0) {
			harmonizedColor = mix(worldColorization.rgb, vec3(0.5), harmonizeStrength); // Max of 1.0
		} else {
			harmonizedColor = mix(vec3(0.5), vec3(1.0) - worldColorization.rgb, (harmonizeStrength - 1.0) * 0.5); // Max of 2.0
		}

    if (colorSafeMode != 0) { // Make sure we don't add brightness
      harmonizedColor *= fragColor.rgb;
    }

		fragColor = vec4(mix(fragColor.rgb, harmonizedColor, worldColorization.a), 1.0);
	}
}
