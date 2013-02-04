#include <stdio.h>
#include <math.h>

int main()
{
	do{
		float rssi;
		float distance;
		printf("\nRSSI: ");
		scanf("%f",&rssi);
		distance = pow(10,((rssi-76.6)/9.32677));
		printf("%.2f",distance);
	}while(1);
	return 0;
}