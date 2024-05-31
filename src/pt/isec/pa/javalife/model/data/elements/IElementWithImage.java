package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementWithImage
        permits Fauna {
        String getImage(); // image path
        void setImage(String image);
}