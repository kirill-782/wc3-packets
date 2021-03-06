package ru.irinabot.protocol.w3gs.entries;

public class SlotRace implements Cloneable {

    static public final byte HUMAN = 1;
    static public final byte ORC = 2;
    static public final byte NIGTELF = 4;
    static public final byte UNDEAD = 8;
    static public final byte RANDOM = 32;
    static public final byte SELECTABLE = 64;

    private boolean selectable = false;
    private byte race = 0;

    public boolean isSelectable() {
        return selectable;
    }

    public byte getRace() {
        return race;
    }


    public SlotRace(byte flags)
    {
        if( ( flags & SELECTABLE ) == SELECTABLE )
            this.selectable = true;

        if( ( flags & HUMAN ) != 0 )
            this.race = HUMAN;
        else if( ( flags & ORC ) != 0 )
            this.race = ORC;
        else if( ( flags & NIGTELF ) != 0 )
            this.race = NIGTELF;
        else if( ( flags & UNDEAD ) != 0 )
            this.race = UNDEAD;
        else if( ( flags & RANDOM ) != 0 )
            this.race = RANDOM;

        if( race == 0 )
            throw new IllegalArgumentException( "Unable to select race from flags." );

    }

    public SlotRace( String race, boolean selectable )
    {
        this.selectable = selectable;

        switch (race) {
            case "human":
                this.race = HUMAN;
                break;
            case "orc":
                this.race = ORC;
                break;
            case "night elf":
                this.race = NIGTELF;
                break;
            case "undead":
                this.race = UNDEAD;
                break;
            case "random":
                this.race = RANDOM;
                break;
        }

        if( this.race == 0 )
            throw new IllegalArgumentException( "Unknown race given." );
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public byte getFlag()
    {
        return (byte) (this.race | (this.selectable ? SELECTABLE : 0));
    }

}
