#include "scalar.h"
 #include <iostream>
 #include <math.h>
 #include <vector>

 using namespace std;

JNIEXPORT jdoubleArray JNICALL Java_libs_Scalar_multi01 (JNIEnv *env, jobject thisObj, jobjectArray arrayA, jobjectArray arrayB){
    double arrayC[]  = {0.625,0.4,0.2,0.033};

     	jsize size = env->GetArrayLength( arrayA );
     	vector<double> input( size );
     	env->GetDoubleArrayRegion( arrayA, 0, size, &input[0] );


     	jdoubleArray output = env->NewDoubleArray( 4 );
     	env->SetDoubleArrayRegion( output, 0, 4, &arrayC[0] );

     	return output;
}