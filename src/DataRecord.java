public record DataRecord(String login, int id, String node_id) {
    @Override
    public String toString() {
        return "login: " + login + '\n' +
                "id: " + id +'\n' +
                "node_id: " + node_id;
    }
}
