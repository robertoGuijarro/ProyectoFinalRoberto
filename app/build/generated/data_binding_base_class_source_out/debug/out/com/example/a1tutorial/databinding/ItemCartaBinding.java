// Generated by view binder compiler. Do not edit!
package com.example.a1tutorial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.example.a1tutorial.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemCartaBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView itemCartaNombre;

  @NonNull
  public final TextView itemCartaPrecio;

  @NonNull
  public final TextView itemCartaTipo;

  private ItemCartaBinding(@NonNull CardView rootView, @NonNull TextView itemCartaNombre,
      @NonNull TextView itemCartaPrecio, @NonNull TextView itemCartaTipo) {
    this.rootView = rootView;
    this.itemCartaNombre = itemCartaNombre;
    this.itemCartaPrecio = itemCartaPrecio;
    this.itemCartaTipo = itemCartaTipo;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCartaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCartaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_carta, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCartaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      TextView itemCartaNombre = rootView.findViewById(R.id.item_carta_nombre);
      if (itemCartaNombre == null) {
        missingId = "itemCartaNombre";
        break missingId;
      }
      TextView itemCartaPrecio = rootView.findViewById(R.id.item_carta_precio);
      if (itemCartaPrecio == null) {
        missingId = "itemCartaPrecio";
        break missingId;
      }
      TextView itemCartaTipo = rootView.findViewById(R.id.item_carta_tipo);
      if (itemCartaTipo == null) {
        missingId = "itemCartaTipo";
        break missingId;
      }
      return new ItemCartaBinding((CardView) rootView, itemCartaNombre, itemCartaPrecio,
          itemCartaTipo);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}