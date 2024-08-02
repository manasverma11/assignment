import itertools


class Chessboard:
    def __init__(self, soldiers, castle):
        self.soldiers = set(soldiers)
        self.castle = castle
        self.path_list = []

    def find_paths(self):
        all_paths = []
        for path in itertools.permutations(self.soldiers):
            path = list(path)
            if self.is_valid_path(path):
                all_paths.append(path)
        self.path_list = all_paths

    def is_valid_path(self, path):
        current_position = self.castle
        visited = set()

        for pos in path:
            if pos not in self.soldiers:
                return False

            if pos not in visited:
                visited.add(pos)

            if not self.can_move_from(current_position, pos):
                return False

            current_position = pos

        return current_position == self.castle

    def can_move_from(self, start, end):
        if start[0] <= end[0] and start[1] != end[1]:
            if end[1] < start[1]:
                return True
        return False

    def print_paths(self):
        for idx, path in enumerate(self.path_list):
            print(f"Path {idx + 1}")
            current_position = self.castle
            for pos in path:
                print(f"Start {current_position}")
                print(f"Kill {pos}. Turn Left")
                current_position = pos
            print(f"Arrive {self.castle}")
            print()


def main():
    # Read input for soldiers
    num_soldiers = int(input("Enter number of soldiers: "))
    soldiers = []
    for i in range(num_soldiers):
        x, y = map(int, input(f"Enter coordinates for soldier {i + 1}: ").split(','))
        soldiers.append((x, y))

    # Read input for castle
    cx, cy = map(int, input("Enter the coordinates for your “special” castle: ").split(','))
    castle = (cx, cy)

    # Create a chessboard instance and find paths
    board = Chessboard(soldiers, castle)
    board.find_paths()

    # Print all unique paths
    print(f"Thanks. There are {len(board.path_list)} unique paths for your ‘special_castle’")
    board.print_paths()


if __name__ == "__main__":
    main()






# import matplotlib.pyplot as plt
# import networkx as nx
# import numpy as np
#
# # Function to get user inputs
# def get_flight_coordinates():
#     flights = {}
#     num_flights = int(input("Enter the number of flights: "))
#
#     for i in range(1, num_flights + 1):
#         flight_name = f"Flight {i}"
#         waypoints = []
#         num_waypoints = int(input(f"Enter the number of waypoints for {flight_name}: "))
#
#         for j in range(num_waypoints):
#             x, y = map(int, input(f"Enter coordinates for waypoint {j + 1} (x y): ").split())
#             waypoints.append((x, y))
#
#         flights[flight_name] = waypoints
#
#     return flights
#
# def create_graph(flights):
#     G = nx.Graph()
#     for flight, waypoints in flights.items():
#         for i in range(len(waypoints) - 1):
#             G.add_edge(waypoints[i], waypoints[i + 1])
#     return G
#
# def find_paths(G, flights):
#     paths = {}
#     for flight, waypoints in flights.items():
#         start, end = waypoints[0], waypoints[-1]
#         if start in G.nodes and end in G.nodes:
#             path = nx.shortest_path(G, source=start, target=end)
#             paths[flight] = path
#         else:
#             paths[flight] = []  # No path found if start or end is not in the graph
#     return paths
#
# def adjust_paths(paths):
#     adjusted_paths = {}
#     for flight, path in paths.items():
#         adjusted_path = []
#         for i, (x, y) in enumerate(path):
#             adjustment = 0.1 * np.sin(i)  # Basic adjustment for visual separation
#             adjusted_path.append((x + adjustment, y + adjustment))
#         adjusted_paths[flight] = adjusted_path
#     return adjusted_paths
#
# def draw_paths(flights, adjusted_paths):
#     plt.figure(figsize=(10, 10))
#     colors = ['r', 'g', 'b', 'c', 'm', 'y', 'k']  # More colors if needed
#
#     for i, (flight, waypoints) in enumerate(flights.items()):
#         xs, ys = zip(*adjust_paths({flight: waypoints})[flight])  # Use adjusted coordinates for plotting
#         plt.plot(xs, ys, color=colors[i % len(colors)], marker='o', label=flight)
#
#     plt.xlabel('X Coordinate')
#     plt.ylabel('Y Coordinate')
#     plt.title('Flight Paths')
#     plt.legend()
#     plt.grid(True)
#     plt.show()
#
# # Main function to run the program
# def main():
#     flights = get_flight_coordinates()
#     G = create_graph(flights)
#     paths = find_paths(G, flights)
#     adjusted_paths = adjust_paths(paths)
#     draw_paths(flights, adjusted_paths)
#
# if __name__ == "__main__":
#     main()
