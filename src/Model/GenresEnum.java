package Model;

public enum GenresEnum {
    AUTOBIOGRAPHY,
    CRIME_FICTION,
    FANTASY,
    HISTORY,
    NARRATIVE,
    PHILOSOPHY_OF_SCIENCE,
    POLITICS,
    SCIENCE_FICTION;

    @Override
    public String toString(){
        switch(this){
            case AUTOBIOGRAPHY:
                return "Autobiography";
            case CRIME_FICTION:
                return "Crime Fiction";
            case FANTASY:
                return "Fantasy";
            case HISTORY:
                return "History";
            case NARRATIVE:
                return "Narrative";
            case PHILOSOPHY_OF_SCIENCE:
                return "Philosophy of Science";
            case POLITICS:
                return "Politics";
            default:
                return "Science Fiction";
        }
    }
}

