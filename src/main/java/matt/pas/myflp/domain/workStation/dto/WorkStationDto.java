package matt.pas.myflp.domain.workStation.dto;

public class WorkStationDto {
    private String name;

    public String getName() {
        return name;
    }

    public WorkStationDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
