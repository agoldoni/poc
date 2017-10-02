package it.galby.poc.preprocessing.impl;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("it.galby.poc.preprocessing.api.GenBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderPreProcessor extends AbstractProcessor {

	 @Override
	    public boolean process(//
	        final Set<? extends TypeElement> annotations,//
	        final RoundEnvironment roundEnv){

	        for (TypeElement element : annotations){
	            System.out.println("aaaaaa111aaa "+element.getQualifiedName());
	        }
	        
	        return true;
	    }
	
}
