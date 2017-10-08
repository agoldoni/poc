package it.galby.poc.preprocessing.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import it.galby.poc.preprocessing.api.GenBuilder;

@SupportedAnnotationTypes("it.galby.poc.preprocessing.api.GenBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderPreProcessor extends AbstractProcessor {

	@Override
	public boolean process(//
			final Set<? extends TypeElement> annotations, //
			final RoundEnvironment roundEnv) {

		for (TypeElement element : annotations) {
			System.out.println("aaaaaa111aaa " + element.getQualifiedName());
		}

		for (Element elem : roundEnv.getElementsAnnotatedWith(GenBuilder.class)) {

			if (elem.getKind() == ElementKind.CLASS) {
				TypeElement telem = (TypeElement) elem;
				try {
					writeBuilderFile(telem);
				} catch (IOException e) {
					e.printStackTrace();
					processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
					return true;
				}

			}

		}

		return true;
	}

	// ***********************************************************************
	// ***********************************************************************

	private void writeBuilderFile(TypeElement elem) throws IOException {

		ClassInfo ci = new ClassInfo(elem.getQualifiedName().toString());
		JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(ci.getClassNameBuilder());

		System.out.println("bbbb " + ci.getPackageName());

		try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
			if (ci.getPackageName() != null) {
				out.print("package ");
				out.print(ci.getPackageName());
				out.println(";");
				out.println();
			}

			out.print("public class ");
			out.print(ci.getBuilderSimpleClassName());
			out.println(" {");
			out.println();
			out.println(" }");

		}

	}

	// ***********************************************************************
	// ***********************************************************************

	class ClassInfo {
		private String className;

		public ClassInfo(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public String getSimpleClassName() {
			int lastDot = className.lastIndexOf('.');
			String simpleClassName = className.substring(lastDot + 1);
			return simpleClassName;
		}

		public String getPackageName() {
			int lastDot = className.lastIndexOf('.');
			String simpleClassName = className.substring(0, lastDot);
			return simpleClassName;
		}

		public String getBuilderSimpleClassName() {
			return getSimpleClassName() + "Builder";
		}

		public String getClassNameBuilder() {
			return className + "Builder";
		}

	}

}
