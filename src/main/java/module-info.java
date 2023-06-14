module MaterialJava {
	requires MaterialFX;
	requires VirtualizedFX;

	requires jdk.localedata;

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	requires fr.brouillard.oss.cssfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;
	requires org.scenicview.scenicview;

	exports com.hbourgeot.inversiones7h;
	opens com.hbourgeot.inversiones7h to javafx.graphics;
	opens com.hbourgeot.inversiones7h.controllers to javafx.fxml;
}