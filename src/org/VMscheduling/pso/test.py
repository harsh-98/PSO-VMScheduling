import matplotlib.pyplot as plt
import matplotlib
import pandas as pd
import numpy as np
from matplotlib.animation import FuncAnimation
fig, ax = plt.subplots()
fig.set_tight_layout(True)




def update(i):
    print(i)
    data = pd.read_csv("data/data" +str(i) +".csv")
    lab = 'timestep {0}'.format(i)
    plt.clf()
    plt.scatter(data['PM'], data['VM'], c="g", alpha=0.5, marker=r'$\clubsuit$',
            label=lab)

if __name__ == "__main__":
    anim = FuncAnimation(fig, update, frames=np.arange(0, 10), interval=100)
    plt.xlabel("Virtual machines")
    plt.ylabel("Physical machines")
    plt.legend(loc=2)
    plt.show()
