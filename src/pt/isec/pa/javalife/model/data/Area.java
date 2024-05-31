package pt.isec.pa.javalife.model.data;

import java.io.Serializable;

import pt.isec.pa.javalife.model.fsm.Direction;

public record Area(double top, double left, double bottom, double right) implements Serializable {

    public static double distance(Area a1,Area a2)
    {
        double deltaX = a1.left() - a2.left();
        double deltaY = a2.top() - a2.top();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    
	public boolean intersects(Area other) {
        return !(this.right <= other.left || this.left >= other.right || 
                 this.bottom <= other.top || this.top >= other.bottom);
    }

    //Retorna area com posição corrigada do objeto que esta em movimento
    public Area solveColision(Direction dir,Area other)
    {
        if(!intersects(other)){ return new Area(-1, -1, -1, -1);}

        double x = this.left;
        double y = this.top;

        final double MARGIN = 1.0;

        if(dir == Direction.LEFT){
            x = other.right + MARGIN;
        }
        else if(dir == Direction.RIGHT){
            x = other.left - (this.right - this.left) - MARGIN;
        }
        else if(dir == Direction.UP){
             y = other.bottom + MARGIN;
        }
        else if(dir == Direction.DOWN){
             y = other.top - (this.bottom - this.top) - MARGIN;
        }   
        else{
            return new Area(-1, -1, -1, -1);
        }

        return new Area(y, x, y + (this.bottom - this.top), x + (this.right - this.left));
    }
}