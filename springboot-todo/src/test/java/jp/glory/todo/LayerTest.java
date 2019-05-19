package jp.glory.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

class LayerTest {

	private static final String ROOT_PACKAGE = "jp.glory.todo";

	private static final String DOMAIN ="Domain";
	private static final String USECASE ="Usecase";
	private static final String INFRA ="Infrastructure";
	private static final String INFRA_REPOSITORY ="Infrastructure Repository";
	private static final String WEB ="Web";
	private static final String SETTING ="Setting";
	private static final String ENCRYPT ="Encrpyt"; // TODO 暫定対応。このレイヤ設定は消したい。

	@Test
	@DisplayName("レイヤのテスト")
	void testLayers() {

		ImportOptions options = new ImportOptions()
					.with(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS);
		JavaClasses importedClasses = new ClassFileImporter(options).importPackages(ROOT_PACKAGE);

		ArchRule rules = Architectures.layeredArchitecture() //
				.layer(DOMAIN).definedBy(ROOT_PACKAGE+ ".context.(*).domain..") //
				.layer(USECASE).definedBy(ROOT_PACKAGE+ ".context.(*).usecase..") //
				.layer(INFRA).definedBy(ROOT_PACKAGE+ ".context.(*).infra..") //
				.layer(INFRA_REPOSITORY).definedBy(ROOT_PACKAGE+ ".context.(*).infra.repository..") //
				.layer(WEB).definedBy(ROOT_PACKAGE+ ".context.(*).web..") //
				.layer(SETTING).definedBy(ROOT_PACKAGE + ".setting..") //
				.layer(ENCRYPT).definedBy(ROOT_PACKAGE + ".external.encrypt") //
				.whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(USECASE, INFRA_REPOSITORY, WEB, SETTING, ENCRYPT) //
				.whereLayer(USECASE).mayOnlyBeAccessedByLayers(WEB, SETTING) //
				.whereLayer(WEB).mayOnlyBeAccessedByLayers(SETTING) //
				.whereLayer(INFRA).mayNotBeAccessedByAnyLayer();

		rules.check(importedClasses);
	}

}
