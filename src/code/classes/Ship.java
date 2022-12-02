package code.classes;

public class Ship {
  private int remainingPassengers;
  private int boxDamage;
  private boolean boxRetrieved;
  private boolean wrecked;
  private boolean destroyed;
  private int x;
  private int y;

  public Ship(int x, int y, int remainingPassengers) {
    this.remainingPassengers = remainingPassengers;
    this.x = x;
    this.y = y;
    this.boxDamage = 0;
    this.destroyed = false;
    this.boxRetrieved = false;
    this.wrecked = false;
  }

  public int getRemainingPassengers() {
    return remainingPassengers;
  }

  public void setRemainingPassengers(int remainingPassengers) {
    this.remainingPassengers = remainingPassengers;
  }

  public boolean isBoxRetrieved() {
    return boxRetrieved;
  }

  public void setBoxRetrieved(boolean boxRetrieved) {
    this.boxRetrieved = boxRetrieved;
  }

  public boolean isWrecked() {
    return wrecked;
  }

  public void setWrecked(boolean wrecked) {
    this.wrecked = wrecked;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public void setDestroyed(boolean destroyed) {
    this.destroyed = destroyed;
  }

  public int getBoxDamage() {
    return boxDamage;
  }

  public void setBoxDamage(int boxDamage) {
    this.boxDamage = boxDamage;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return (
      "ship [boxRetrieved=" +
      boxRetrieved +
      ", remainingPassengers=" +
      remainingPassengers +
      ", wrecked=" +
      wrecked +
      "]"
    );
  }

  public void saveKill() {
    this.remainingPassengers--;
    //No more passengers
    if (remainingPassengers == 0) {
      this.wrecked = true;
    }
  }

  public void damageBox() {
    this.boxDamage++;
    //Box is destroyed
    if (boxDamage == 20) {
      this.destroyed = true;
    }
  }

  public void retrieveBox() {
    this.boxRetrieved = true;
  }

  //copy ship
  public Ship copy(){
    Ship ship = new Ship(this.x, this.y, this.remainingPassengers);
    ship.setBoxDamage(this.boxDamage);
    ship.setBoxRetrieved(this.boxRetrieved);
    ship.setDestroyed(this.destroyed);
    ship.setWrecked(this.wrecked);
    return ship;
  }
}
