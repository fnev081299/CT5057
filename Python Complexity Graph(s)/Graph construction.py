from matplotlib import pyplot as plt

plt.plot([200000, 400000, 600000, 800000, 1000000], [6869, 899773, 1412901, 1720431, 2007310], label='Merge')
plt.scatter([200000, 400000, 600000, 800000, 1000000], [6869, 899773, 1412901, 1720431, 2007310],  label='Merge')

plt.plot([200000, 400000, 600000, 800000, 1000000], [12016, 867179, 1272360, 1659645, 2097588], label='Binary Search')
plt.scatter([200000, 400000, 600000, 800000, 1000000], [12016, 867179, 1272360, 1659645, 2097588],  label='Binary '
                                                                                                            'Search')

# y value is used tof the input to be added to the graph
plt.plot([200000, 400000, 600000, 1000000], [3575544,  3975921, 4066867, 8940478], label='A*')
plt.scatter([200000, 400000, 600000, 1000000], [3575544,  3975921, 4066867, 8940478], label='A*')
plt.legend()
plt.xlabel("Elements")
plt.ylabel("Time")
plt.show()
