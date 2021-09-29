package model.api;

import java.util.List;

public class Playlist
{
    private int id;

    private String name;

    private boolean owner;

    private List<String> tracks;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isOwner()
    {
        return owner;
    }

    public void setOwner(boolean owner)
    {
        this.owner = owner;
    }

    public List<String> getTracks()
    {
        return tracks;
    }

    public void setTracks(List<String> tracks)
    {
        this.tracks = tracks;
    }
}
