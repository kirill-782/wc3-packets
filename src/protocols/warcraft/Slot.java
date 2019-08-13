package protocols.warcraft;

import protocols.warcraft.util.SlotComputerType;
import protocols.warcraft.util.SlotRace;
import protocols.warcraft.util.SlotStatus;

import java.nio.ByteBuffer;

public class Slot {

    private byte playerID = 0;
    private byte downloadStatus = 0;
    private SlotStatus slotStatus = SlotStatus.OPEN;
    private boolean computer = false;
    private byte team = 0;
    private byte colour = 0;
    private SlotRace race = new SlotRace("night elf", false);
    private SlotComputerType computerType = SlotComputerType.NORMAL;
    private byte handicap = 100;

    public Slot()
    {
    }

    public Slot(ByteBuffer b)
    {
        this.playerID = b.get();
        this.downloadStatus = b.get();
        this.slotStatus = SlotStatus.getInstance(b.get());
        this.computer = b.get() != 0;
        this.team = b.get();
        this.colour = b.get();
        this.race = new SlotRace(b.get());
        this.computerType = SlotComputerType.getInstance(b.get());
        this.handicap = b.get();
    }

    public byte[] assemble( )
    {
        ByteBuffer b = ByteBuffer.allocate(9);
        b.put(this.playerID);
        b.put(this.downloadStatus);
        b.put(this.slotStatus.getStatus());
        b.put(this.computer ? (byte) 1 : 0);
        b.put(this.team);
        b.put(this.colour);
        b.put(this.race.getFlag());
        b.put(this.computerType.toByte());
        b.put(this.handicap);
        return b.array();
    }

    public byte getPlayerID() {
        return playerID;
    }

    public Slot setPlayerID(byte playerID) {
        this.playerID = playerID;
        return this;
    }

    public byte getDownloadStatus() {
        return downloadStatus;
    }

    public Slot setDownloadStatus(byte downloadStatus) {
        this.downloadStatus = downloadStatus;
        return this;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public Slot setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
        return this;
    }

    public boolean isComputer() {
        return computer;
    }

    public Slot setComputer(boolean computer) {
        this.computer = computer;
        return this;
    }

    public byte getTeam() {
        return team;
    }

    public Slot setTeam(byte team) {
        this.team = team;
        return this;
    }

    public byte getColour() {
        return colour;
    }

    public Slot setColour(byte colour) {
        this.colour = colour;
        return this;
    }

    public SlotRace getRace() {
        return race;
    }

    public Slot setRace(SlotRace race) {
        this.race = race;
        return this;
    }

    public SlotComputerType getComputerType() {
        return computerType;
    }

    public Slot setComputerType(SlotComputerType computerType) {
        this.computerType = computerType;
        return this;
    }

    public byte getHandicap() {
        return handicap;
    }

    public Slot setHandicap(byte handicap) {
        this.handicap = handicap;
        return this;
    }

}
